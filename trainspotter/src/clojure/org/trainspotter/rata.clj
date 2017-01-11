(ns org.trainspotter.rata
  (:require [clj-http.lite.client :as client]
            [cheshire.core :as json]
            [org.trainspotter.utils :as utils]))

(def ^:const API_URL "https://rata.digitraffic.fi/api/v1/")

(def ^:const TRAIN_ID 8461)

(defmacro api-query [& more]
  `(let [response# (client/get (str API_URL ~@more) {:accept :json})
         status# (:status response#)]
     (assert (= status# 200) (str "Error:" status#))
     (json/parse-string (:body response#) true)))

(defn get-stations []
  (api-query "metadata/stations"))

(defn get-station-names []
  (map :stationName (get-stations)))

(defn get-train [train-id]
  (api-query "live-trains/" train-id))

(defn get-schedules [from to date]
  (api-query "schedules?departure_station=" from
             "&arrival_station=" to
             "&departure_date=" date))

(defn get-schedule-for-train [from to ^org.joda.time.DateTime date-time]
  (let [date-time-str (utils/date-time-to-str date-time)]
    (api-query "schedules?departure_station=" from
               "&arrival_station=" to
               "&from=" date-time-str
               "&limit=1")))
