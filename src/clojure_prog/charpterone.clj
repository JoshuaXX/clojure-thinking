(ns clojure-prog.charpterone)


;;
;; Give all possible FCs
;;
(defn next-take [origin-vect left-over]
  (if (or (nil? left-over)
          (empty? left-over))
    `((~origin-vect nil))
    (map (fn [pos]
           `(~(conj origin-vect (take pos left-over))
             ~(take-last (- (count left-over) pos) left-over)))
         (range 1 (inc (count left-over))))))


(defn split-all [& segs]
  (loop [result    nil
         candidate `(([] ~segs))]
    (if (or (nil? candidate)
            (empty? candidate))
      result
      (let [mid-result (reduce concat
                               (map (fn [[origin left]]
                                      (next-take origin left))
                                    candidate))]
        (recur (concat result (filter (fn [[[_] left]]
                                        (nil? left))
                                      mid-result))
               (filter (fn [[[_] left]]
                         (not (nil? left)))
                       mid-result))))))

(defn combinations [& segs]
  (map (fn [[left right]]
         left)
       (apply split-all segs)))





(= 8 (count (combinations '(a b) '(b c) '(c d) '(d a))))

   



