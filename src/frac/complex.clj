(ns frac.complex)

(defn mul [z1 z2]
  (let [r1 (first z1)
        i1 (second z1)
        r2 (first z2)
        i2 (second z2)]
    [(- (* r1 r2) (* i1 i2)) (+ (* r1 i2) (* r2 i1))]))

(defn add [z1 z2]
  (let [r1 (first z1)
        i1 (second z1)
        r2 (first z2)
        i2 (second z2)]
    [(+ r1 r2) (+ i1 i2)]))

(defn modu [z]
  (let [r (first z)
        i (second z)]
    (Math/sqrt (+ (* r r) (* i i)))))