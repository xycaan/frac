(ns frac.core
  (:require [quil.core :as q])
  (:require [seesaw.core :as s]))
 
(def draw? (atom false))  
(def maxits 50)

(s/native!)
(def f (s/frame :title "Mandelbrot Generator"))
(def xtext (s/text :text "0" :editable? true :columns 3))
(def ytext (s/text :text "0" :editable? true :columns 3))
(def rtext (s/text :text "0" :editable? true :columns 3))
(def gbutton (s/button :text "GENERATE"))


(defn mandelbrot [^double x ^double y]
 (loop [r x i y cnt 0]
   (let [rsqr (* r r)
         isqr (* i i)]
     (if (or (>= cnt ^int maxits) (>= (+ rsqr isqr) 4)) cnt 
       (recur (+ (- rsqr isqr) x) (+ (* 2 i r) y) (inc cnt))))))

(defn gen [^double cenx ^double ceny ^double r]
  (let [^ints p (q/pixels)
        ^int w (q/width)
        ^int h (q/height)
        scalex (/ (* 2 r) w)
        scaley (/ (* 2 r) h)
        xlist (float-array (range (- cenx r) (+ cenx r) scalex))
        ylist (float-array (range (- ceny r) (+ ceny r) scaley))]
    (doseq [^int x (range w) ^int y (range h)]
      (let [m (mandelbrot (aget xlist x) (aget ylist y))
            ^int c (if (= m maxits) 0 m)]
        (aset p (+ x (* y w)) (q/color ^double (* 1.5 c) ^double (* 5.2 c) ^double (* 3.8 c))))))
  (q/update-pixels))

(defn parsetod [d]
  (if (re-find #"^-?\d+\.?\d*$" d) (read-string d) 0))
  

(defn setup []
  ;(gen 0 0 2)
  (s/config! f :content (s/left-right-split (s/left-right-split xtext ytext) (s/left-right-split rtext gbutton)) :resizable? false)
  (->> f s/show! s/pack!)
  (s/listen gbutton :mouse-clicked (fn [e] (if (= @draw? false) (swap! draw? #(not %))))))

(defn draw []
  (if (= @draw? true) (do (swap! draw? #(not %)) (gen (parsetod (s/config xtext :text)) (parsetod (s/config ytext :text)) (let [g (parsetod (s/config rtext :text))] (if (= g 0) 1 g))))))

(defn click []
  (let [mx (q/mouse-x)
        my (q/mouse-y)
        x (parsetod (s/config xtext :text))
        y (parsetod (s/config ytext :text))
        s (* 2 (let [g (parsetod (s/config rtext :text))] (if (= g 0) 1 g)))]
    (s/config! xtext :text (+ x (* (- mx (/ (q/width) 2)) (/ s (q/width)))))
    (s/config! ytext :text (+ y (* (- my (/ (q/height) 2)) (/ s (q/height)))))))

(q/defsketch example
  :title "Frac"
  :setup setup
  :draw draw
  :size [900 900]
  :mouse-clicked click)
                
(defn -main [& args])
    