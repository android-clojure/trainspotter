(ns org.trainspotter.rata
  (:require [clj-http.lite.client :as client]))

(def ^:const API_URL "https://rata.digitraffic.fi/api/v1/")

(def ^:const TRAIN_ID 8461)

(client/get (str API_URL "live-trains/" TRAIN_ID) {:accept :json})
