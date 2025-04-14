package tech.fiap.project.strategy;

import org.springframework.stereotype.Component;
import tech.fiap.project.dto.VideoStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EmailStrategyFactory {

    private final Map<VideoStatus, EmailStrategy> strategies = new HashMap<>();

    public EmailStrategyFactory(List<EmailStrategy> strategyList) {
        for (EmailStrategy strategy : strategyList) {
            if (strategy instanceof OkStatusEmailStrategy) {
                strategies.put(VideoStatus.FINALIZADO, strategy);
            } else if (strategy instanceof ErrorStatusEmailStrategy) {
                strategies.put(VideoStatus.ERRO, strategy);
            }
        }
    }

    public Optional<EmailStrategy> getStrategy(String status) {
        try {
            VideoStatus videoStatus = VideoStatus.valueOf(status.toUpperCase());
            return Optional.ofNullable(strategies.get(videoStatus));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
