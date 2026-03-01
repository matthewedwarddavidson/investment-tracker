(ns investment-tracker.db.migrate
  (:require [integrant.core :as ig]
            [migratus.core :as migratus]
            [taoensso.timbre :as log]))

(defn- migratus-config [datasource]
  {:store         :database
   :migration-dir "migrations"
   :db            {:datasource datasource}})

(defmethod ig/init-key :investment-tracker.db.migrate/migrations
  [_ {:keys [datasource]}]
  (log/info "Running database migrations")
  (migratus/migrate (migratus-config datasource))
  nil)

(defn migrate
  "Run pending migrations — intended for use via `clojure -X`."
  [_opts]
  (let [{:keys [_datasource]} (require 'investment-tracker.system)
        sys ((resolve 'investment-tracker.system/start) :dev)]
    (try
      (migratus/migrate (migratus-config (:investment-tracker.db/datasource sys)))
      (finally
        ((resolve 'investment-tracker.system/stop) sys)))))

(defn rollback
  "Roll back the last migration — intended for use via `clojure -X`."
  [_opts]
  (let [{:keys [_datasource]} (require 'investment-tracker.system)
        sys ((resolve 'investment-tracker.system/start) :dev)]
    (try
      (migratus/rollback (migratus-config (:investment-tracker.db/datasource sys)))
      (finally
        ((resolve 'investment-tracker.system/stop) sys)))))
