(ns org.trainspotter.rata.database
  (:require [neko.data.sqlite :as db]
            [org.trainspotter.log :as log]))

(def trainspotter-schema
  (db/make-schema
    :name "trainspotter.db"
    :version 1
    :tables {:trains
             {:columns
              {:_id "integer primary key"
               :train-id "integer not null default '0'"}}}))

(def get-db-helper
  (memoize
    (fn [] (db/create-helper trainspotter-schema))))

(defn- trainspotter-db [] (db/get-database (get-db-helper) :write))

(defn get-id [entry-seq]
  (:_id entry-seq))

(defn add-train [train-id]
  (log/d "add train to watch: " train-id)
  (db/insert (trainspotter-db) :trians {:train-id train-id}))
