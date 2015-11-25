(defproject frac "1.0"
  :description "Mandelbrot set"
  :url "http://github.com/xycaan"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [quil "2.2.6" :exclusions [org.clojure/clojure]]
                 [seesaw "1.4.5"]]
  :main frac.core)
