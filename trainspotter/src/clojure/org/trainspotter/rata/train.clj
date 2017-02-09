(ns org.trainspotter.rata.train)

(defn get-id [train]
  (:trainNumber train))

(defn running? [train]
  (= (:runningCurrently train) true))

(defn get-stops [time-table station]
  (filter #(= (:stationShortCode %) station) time-table))

(defn get-cancelled-stops [stops]
  (not (empty? (filter #(:cancelled %) stops))))

(defn cancelled? [train station]
  (or (:cancelled train) ;; <- TODO is this needed?
       (get-cancelled-stops (get-stops (:timeTableRows train) station))))
