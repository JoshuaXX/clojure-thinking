(ns clojure-prog.profiling
  (:require [taoensso.tufte :as tufte]))


(defn get-x [] (Thread/sleep 500)             "x val")
(defn get-y [] (Thread/sleep (rand-int 1000)) "y val")


(tufte/add-handler! :customized-handler
                    (fn [args]
                      (println (tufte/format-pstats (:pstats args)))))

#_(tufte/profile
 {:dynamic? true}
 (dotimes [_ 5]
    (tufte/p (resolve 'get-x) (get-x))
    (tufte/p (resolve 'get-y) (get-y)))
 @(future
   (dotimes [_ 5]
    (tufte/p (resolve 'get-x) (get-x))
    (tufte/p (resolve 'get-y) (get-y))))
  "Done")
