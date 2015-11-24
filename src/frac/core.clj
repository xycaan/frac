(ns frac.core
  (:require [quil.core :as q])
  (:require [frac.complex :refer :all]))

(def wi 250)
(def he 250)

(def scale (atom (* 0.0008 he)))
(def xoff (atom (- 0 (/ he 2))))
(def yoff (atom (- 0 (/ he 2))))
(def its 50)

(defn mandelbrot [r i]
 (count (take its (take-while #(<= (modu %) 2) (iterate #( add (mul % %) [r i]) [r i])))))

(defn gen []
  (let [p (q/pixels)
        w (q/width)
        h (q/height)]
    (doseq [x (range w) y (range h)]
      (let [m (mandelbrot (* @scale (+ x @xoff)) (* @scale (+ y @yoff)))
            c (if (= m its) 0 m)]
        (aset p (+ x (* y w)) (q/color (* 1.5 c) (* 5.2 c) (* 3.8 c))))))
  (q/update-pixels))

(defn setup []
  (gen))

(defn draw [])

(defn click []
  (swap! xoff #(+ (q/mouse-x) (- (/ (q/width) 2)) %))
  (swap! yoff #(+ (q/mouse-y) (- (/ (q/height) 2)) %)))

(defn wheel [z] 
  (swap! scale #(if (pos? z) (* 1.1 %) (* 0.9 %)))
  (prn @scale))

(defn keyp []
  (gen))

(q/defsketch example
  :title "Mandel"
  :setup setup
  :draw draw
  :size [wi he]
  :mouse-clicked click
  :mouse-wheel wheel
  :key-pressed keyp)
                
(defn -main [& args])
    