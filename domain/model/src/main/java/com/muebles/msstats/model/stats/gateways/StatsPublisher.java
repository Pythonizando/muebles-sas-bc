public interface StatsPublisher {
    Mono<Void> publishStats(Stats stats);
}
