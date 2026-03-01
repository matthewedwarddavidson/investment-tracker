default:
    @just --list

run:
    clojure -M:run

test:
    clojure -X:test

repl:
    clojure -A:dev

docker-build:
    docker build -t investment-tracker .

docker-run:
    docker run -p 3000:3000 investment-tracker

migrate:
    clojure -X:dev investment-tracker.db.migrate/migrate

rollback:
    clojure -X:dev investment-tracker.db.migrate/rollback
