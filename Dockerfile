FROM clojure:temurin-21-tools-deps-alpine AS builder

WORKDIR /app
COPY deps.edn ./
RUN clojure -P

COPY . .
RUN clojure -M -e "(compile 'investment-tracker.core)"

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=builder /app .

EXPOSE 3000

CMD ["clojure", "-M:run"]
