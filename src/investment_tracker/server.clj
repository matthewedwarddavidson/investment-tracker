(ns investment-tracker.server
  (:require [integrant.core :as ig]
            [ring.adapter.jetty :as jetty]
            [taoensso.timbre :as log]))

(defmethod ig/init-key :investment-tracker.server/server
  [_ {:keys [port handler]}]
  (log/info "Starting HTTP server on port" port)
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/halt-key! :investment-tracker.server/server
  [_ server]
  (log/info "Stopping HTTP server")
  (.stop server))
