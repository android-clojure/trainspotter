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
        station "LPV"
        train-name "U"
        departure-time "07:17"]
    (trainspotter/add-train train-id station train-name departure-time)
    (is (= (db/query-seq (trainspotter/trainspotter-db) :trains {:_id 1})
           (list {:_id 1
                  :train_id train-id
                  :station station
                  :name train-name
                  :time departure-time})))))
(deftest get-train
  (let [train-id 2468
        station "LPV"
        train-name "U"
        departure-time "07:17"]
    (trainspotter/add-train train-id station train-name departure-time)
    (is (= (trainspotter/get-train train-id)
           {:_id 1
            :train_id train-id
            :station station
            :name train-name
            :time departure-time}))))

(deftest get-trains
  (let [trains {1 "LPV" 2 "KPO" 3 "JRS" 4 "HKI"}]
    (doseq [train trains]
      (trainspotter/add-train (first train) (second train) "U" "11:22"))
    (is (= (trainspotter/get-trains)
           '({:train_id 1 :station "LPV" :name "U" :time "11:22"}
             {:train_id 2 :station "KPO" :name "U" :time "11:22"}
             {:train_id 3 :station "JRS" :name "U" :time "11:22"}
             {:train_id 4 :station "HKI" :name "U" :time "11:22"})))))
