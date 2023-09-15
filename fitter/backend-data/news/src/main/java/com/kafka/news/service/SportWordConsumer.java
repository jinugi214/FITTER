package com.kafka.news.service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kafka.news.entity.SportWord;
import com.kafka.news.repository.SportWordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SportWordConsumer {

	private final SportWordRepository sportWordRepository;
	private static final String TOPIC = "sport_topic";
	private static final ConcurrentMap<String, Integer> sportCountMap = new ConcurrentHashMap<>();

	Logger logger = LoggerFactory.getLogger(SportWordConsumer.class);

	@KafkaListener(topics = TOPIC, groupId = "keywordCount")
	public void receiveMessage(String message) throws IOException {
		sportCountMap.compute(message, (key, value) -> value == null ? 1 : value + 1);
	}

	@Scheduled(fixedDelay = 60000)
	public void saveToDB() {
		for (Map.Entry<String, Integer> entry : sportCountMap.entrySet()) {
			String word = entry.getKey();
			Integer count = entry.getValue();

			Optional<SportWord> optionalSportWord = sportWordRepository.findByName(word);

			if (optionalSportWord.isPresent()) {
				SportWord existingSportWord = optionalSportWord.get();
				existingSportWord.updateCount(count);
				sportWordRepository.save(existingSportWord);
			} else {
				SportWord newSportWord = SportWord.builder()
					.name(word)
					.count(count)
					.build();
				sportWordRepository.save(newSportWord);
				logger.info("sport-keyworkd: {}", newSportWord);
			}
		}
		sportCountMap.clear();
	}
}
