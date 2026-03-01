(ns investment-tracker.test-runner
  (:require [eftest.runner :as eftest]))

(defn run-tests
  "Discovers and runs all tests under the test directory."
  [_opts]
  (eftest/run-tests (eftest/find-tests "test")))
