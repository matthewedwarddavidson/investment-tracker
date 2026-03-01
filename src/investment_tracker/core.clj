(ns investment-tracker.core
  (:require [investment-tracker.system :as system]
            [taoensso.timbre :as log]))

(defn -main
  "Entry point — starts the production system."
  [& _args]
  (log/info "Starting investment-tracker")
  (let [sys (system/start :prod)]
    (.addShutdownHook (Runtime/getRuntime)
                      (Thread. ^Runnable (fn [] (system/stop sys))))
    sys))
