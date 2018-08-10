(ns redis-demo.core
  (:require [taoensso.carmine :as car]
            [config.core :as config]
            [clojure.math.combinatorics :as combo]))

;;
;; function result cached in redis
;;
(def server-conn {:pool {}
                  :spec {:host "127.0.0.1"
                         :port 6379}})

(defmacro wcar* [& body] `(car/wcar server-conn ~@body))


(defmacro defn-redis-cached [function-name param-vector & body]
  (let [cache-result-symbol (gensym)
        param-vector-var    (vec (filter #(not (= '& %)) param-vector))]
    `(defn ~function-name ~param-vector
       (let [[[~cache-result-symbol]] (wcar* :as-pipeline (car/get (str ~function-name ~param-vector-var)))]
         (if ~cache-result-symbol
           ~cache-result-symbol
           (let [~cache-result-symbol (do
                                        ~@body)]
             (wcar* :as-pipeline (car/set (str ~function-name ~param-vector-var) [~cache-result-symbol] :EX (:redis-timeout config/env)))
             ~cache-result-symbol))))))

(defn-redis-cached test-args [number]
  number)
