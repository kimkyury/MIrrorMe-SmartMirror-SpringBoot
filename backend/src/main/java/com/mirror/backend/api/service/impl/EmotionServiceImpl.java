package com.mirror.backend.api.service.impl;

import com.mirror.backend.api.dto.EmotionDto;
import com.mirror.backend.api.entity.Emotion;
import com.mirror.backend.api.entity.EmotionCount;
import com.mirror.backend.api.entity.keys.EmotionKey;
import com.mirror.backend.api.repository.EmotionCountRepository;
import com.mirror.backend.api.repository.EmotionRepository;
import com.mirror.backend.api.service.EmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmotionServiceImpl implements EmotionService {

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private EmotionCountRepository emotionCountRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveEmotion(EmotionDto.EmotionReq emotionReq) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(emotionReq.getEmotionDate(), formatter);

        Emotion emotion = Emotion.builder()
                .emotionCode(emotionReq.getEmotionCode())
                .emotionDate(date)
                .userId(emotionReq.getUserId())
                .createAt(LocalDateTime.now())
                .build();
        Emotion emotionEntity = emotionRepository.save(emotion);

        List<Integer> emotionList = emotionReq.getEmotionList();
        for(int i=0; i<emotionList.size(); i++) {
            Integer myEmotion = emotionList.get(i);
            if(myEmotion == 0) continue;

            EmotionKey emotionKey = EmotionKey.builder()
                    .emotionId(emotionEntity.getEmotionId())
                    .emotionCode(i+1)
                    .build();

            EmotionCount emotionCount = EmotionCount.builder()
                    .emotionKey(emotionKey)
                    .emotionCount(myEmotion).build();

            emotionCountRepository.save(emotionCount);
        }

        return emotionEntity.getEmotionId();
    }

    @Override
    public List<Emotion> getMyEmotion(Long userId) {
        // 일주일간의 감정 보여주기
        LocalDate now = LocalDate.now();
        LocalDate sevenDayAgo = now.minusDays(7);

        List<Emotion> myEmotionList = emotionRepository.findAllByEmotionDateBetween(sevenDayAgo, now);
        return myEmotionList;
    }
}
