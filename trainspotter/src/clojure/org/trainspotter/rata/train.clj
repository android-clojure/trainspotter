(ns org.trainspotter.rata.train)

(defn get-id [train]
  (:trainNumber train))

(defn is-running? [train]
  (= (:runningCurrently train) true))
