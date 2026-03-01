(ns investment-tracker.system
  (:require [integrant.core :as ig]
            [investment-tracker.config :as config]
            [investment-tracker.db]
            [investment-tracker.db.migrate]
            [investment-tracker.server]
            [investment-tracker.handler]))

(defn system-config
  "Builds the Integrant system config for the given profile."
  [profile]
  (let [cfg (config/read-config profile)]
    {:investment-tracker.db/datasource         {:jdbc-url (get-in cfg [:db :jdbc-url])}
     :investment-tracker.db.migrate/migrations {:datasource (ig/ref :investment-tracker.db/datasource)}
     :investment-tracker.handler/routes        {:datasource (ig/ref :investment-tracker.db/datasource)}
     :investment-tracker.server/server         {:port    (get-in cfg [:server :port])
                                                :handler (ig/ref :investment-tracker.handler/routes)}}))

(defn start
  "Starts the system for the given profile."
  [profile]
  (-> (system-config profile)
      ig/init))

(defn stop
  "Stops a running system."
  [system]
  (ig/halt! system))
