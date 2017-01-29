(ns org.trainspotter.t-database
  (:require [clojure.test :refer :all]
            [org.trainspotter.database :as trainspotter]
            [neko.data.sqlite :as db])
  (:import
    [android.app Activity]
    org.robolectric.RuntimeEnvironment
    neko.App))

(set! App/instance RuntimeEnvironment/application)

(defn db-fixture [f]
  (assert (= () (db/query-seq (trainspotter/trainspotter-db) :trains {})))
  (f)
  (trainspotter/wipe))

(use-fixtures :each db-fixture)

(deftest add-train
  (let [train-id 2468
        station "LPV"]
    (trainspotter/add-train train-id station)
    (is (= (db/query-seq (trainspotter/trainspotter-db) :trains {:_id 1})
           (list {:_id 1
                  :train_id train-id
                  :station station})))))
(deftest get-train
  (let [train-id 2468
        station "LPV"]
    (trainspotter/add-train train-id station)
    (is (= (trainspotter/get-train train-id)
           {:_id 1
            :train_id train-id
            :station station}))))

(deftest get-trains
  (let [trains {1 "LPV" 2 "KPO" 3 "JRS" 4 "HKI"}]
    (doseq [train trains]
      (trainspotter/add-train (first train) (second train)))
    (is (= (trainspotter/get-trains)
           '({:train_id 1 :station "LPV"}
             {:train_id 2 :station "KPO"}
             {:train_id 3 :station "JRS"}
             {:train_id 4 :station "HKI"})))))
