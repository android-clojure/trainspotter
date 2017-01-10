(ns org.trainspotter.rata
  (:require [clj-http.lite.client :as client]
            [cheshire.core :as json]))

(def ^:const API_URL "https://rata.digitraffic.fi/api/v1/")

(def ^:const TRAIN_ID 8461)

(defmacro api-query [& more]
  `(let [response# (client/get (str API_URL ~@more) {:accept :json})
        status# (:status response#)]
    (assert (= status# 200) (str "Error:" status#))
    (json/parse-string (:body response#))))

(defn get-stations []
  (api-query "metadata/stations"))

(defn get-train [train-id]
  (api-query "live-trains/" train-id))
