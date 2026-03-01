(ns investment-tracker.handler-test
  (:require [clojure.test :refer [deftest is testing]]
            [investment-tracker.handler :as handler]))

(deftest health-handler-test
  (testing "returns 200 with ok status"
    (let [response (handler/health-handler {})]
      (is (= 200 (:status response)))
      (is (= {:status "ok"} (:body response))))))
