(ns investment-tracker.test-helpers
  (:require [next.jdbc.connection :as connection]
            [migratus.core :as migratus])
  (:import [com.zaxxer.hikari HikariDataSource]))

(defn with-test-datasource
  "Creates an in-memory SQLite datasource, runs migrations, calls f with it, then closes."
  [f]
  (let [ds (connection/->pool HikariDataSource {:jdbcUrl "jdbc:sqlite::memory:"})]
    (try
      (migratus/migrate {:store         :database
                         :migration-dir "migrations"
                         :db            {:datasource ds}})
      (f ds)
      (finally
        (.close ds)))))
