(ns org.trainspotter.service
  (:require
    [org.trainspotter.log :as log]
    ; [org.trainspotter.broadcastreceiver :as tick]
    [org.trainspotter.database :as db])
  (:import [android.app Service]
           [android.os Handler HandlerThread])
  (:gen-class
    :prefix "service-"
    :extends android.app.Service
    :state state
    :init init
    ))

; (def tick-receiver (atom nil))

;; little functions to safely set the fields.
(defn- setfield
  [^org.trainspotter.service this key value]
  (swap! (.state this) into {key value}))

(defn- getfield
  [^org.trainspotter.service this key]
  (@(.state this) key))

(defn service-init []
  [[] (atom {:service-handler nil})])

(defn service-onCreate [this]
  (let [thread (android.os.HandlerThread.
                 "LocationServiceThread")]
    (log/d "service create thread id " (Thread/currentThread))
    (.start thread)
    (setfield this :service-handler (android.os.Handler. (.getLooper thread)))
    (log/i "Service created")))

(defn service-onStartCommand [^org.trainspotter.service this intent flags start-id]
  (let [state (.state this)
        ^android.os.Handler service-handler (getfield this :service-handler)]
    (log/i (str "Service id: " start-id " started"))

    ; TICKs are handled in tick-func by the service-handler thread
    ; (reset! tick-receiver (tick/register-receiver this tick-func service-handler))
    Service/START_STICKY
    ))

(defn service-onDestroy [this]
  (log/i "Service destroyed")
  ; (tick/unregister-receiver this @tick-receiver)
  ; (reset! tick-receiver nil)
  )
