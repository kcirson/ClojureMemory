(ns ClojureMemory.controller

(:use compojure.core)
  (:require [compojure.core :as compojure]
            [ClojureMemory.view :as view]
            [ClojureMemory.model :as model]))

(defn start-page []
  (model/reset-game!)
  (view/play-screen))

(defn turn-page [button-pressed]
  (let [button-id (name (first (keys button-pressed)))
        rownr (Integer/parseInt (str (second button-id)))
        colnr (Integer/parseInt (str (nth button-id 2)))]
    (if (= (model/get-board-cell rownr colnr) \-)
      (if (= (model/get-turn) 1)
        (model/play! rownr colnr)
        (model/play! rownr colnr 2)
      )
    )
    
    (if (model/full-board?)
        (let [winner (model/checkWinner)]
          (if (not= winner "Draw!")
            (view/winner-screen winner)
            (view/draw-screen)
          )
        )
        (view/play-screen)
     )
   )
)

(defroutes memory-routes
  (GET "/" [] (start-page))
  (POST "/" {button-pressed :params} (turn-page button-pressed)))