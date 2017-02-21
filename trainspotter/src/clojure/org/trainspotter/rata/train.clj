(ns org.trainspotter.rata.train)

(defn get-id [train]
  (:trainNumber train))

(defn get-name [train]
  (:commuterLineID train))

(defn running? [train]
  (= (:runningCurrently train) true))

(defn get-time-table [train]
  (:timeTableRows train))

; (defn get-stops [time-table station]
;   (filter #(= (:stationShortCode %) station) time-table))

(defn first-station [train]
  {:post (= (:type %) "DEPARTURE")}
  (first (get-time-table train)))

(defn last-station [train]
  {:post (= (:type %) "ARRIVAL")}
  (last (get-time-table train)))

(defn get-stop [train station stop-type]
  {:pre (keyword? stop-type)}
  (let [time-table (get-time-table train)
        stype (case stop-type
                :departure "DEPARTURE"
                :arrival "ARRIVAL"
                "DEPARTURE")]
    (first
      (filter #(and (= (:stationShortCode %) station)
                    (= (:type %) stype)) time-table))))

(defn get-cancelled-stops [stops]
  (seq (filter :cancelled stops)))

; (defn late? [train station]
;   (< 0 (:differenceInMinutes
;          (get-stop
;            train
;            station
;            :departure))))

(defn get-late-info [train station]
  (let [late-info (select-keys
                    (get-stop
                      train
                      station
                      :departure)
                    [:differenceInMinutes :causes])]
    (assoc late-info :late (< 0 (:differenceInMinutes late-info)))))

(defn cancelled? [train station]
  (or (:cancelled train) ;; <- TODO is this needed?
      (:cancelled (get-stop train station :departure))))
