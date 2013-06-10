(ns ClojureMemory.view

(:use hiccup.form
        [hiccup.def :only [defhtml]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [html5 include-css include-js]])
  (:require [ClojureMemory.model :as model]))

(defhtml layout [& content]
  (html5
   [:head
    [:title "Welcome to Memory"]
    (include-css "/css/memory.css")]
   [:body [:div#wrapper content]]))

(defn cell-html [rownum colnum cell with-submit?] 
  [:td 
   [:input {:name (str "b" rownum colnum)
            :id (str "b" rownum colnum)
            :value (str cell)
            :type (if 
                    (and with-submit?
                         (= (model/get-board-cell rownum colnum) \-)
                         ) 
                    "submit" 
                    "button")
            :class(if (= (model/get-board-cell rownum colnum) \-)
                    "notclicked"
                    "clicked"
                    )
            :style (if
                     (and
                       (not= (model/get-board-cell rownum colnum) \-)
                              (= (model/get-board-value (model/get-scoreboard) rownum colnum)  \=) 
                     )
                     (str "color: #fff; border: 0px solid white; background: #fff;") 
                     (str "display: inline-block;") 
                     )
            }]])
  
(defn row-html [rownum row with-submit?]
  [:tr (map-indexed (fn [colnum cell]
                      (cell-html rownum colnum cell with-submit?))
                    row)])
  
(defn board-html [board with-submit?]
  (form-to [:post "/" ]
           [:table 
            (map-indexed (fn [rownum row]
                           (row-html rownum row with-submit?)) 
                         board)]))

(defn play-screen []
  (layout
    
    [:div 
     [:p "Player " (model/get-player) ", it is your turn!"]
     (board-html (model/get-board) true)]
    [:div#score
     [:p "Player 1 Score: " (model/get-player-score \1)]
     [:p "Player 2 Score: " (model/get-player-score \2)]
    ]
    [:div#cheatsheet
     [:p "Cheatsheet"]
     (board-html (model/get-memoryboard) true)
     ]
    [:input {
      :id "hiddenvals"
      :type "hidden"
      :value (model/get-memoryboard)
     }]
    ))

(defn winner-screen [winner]
  (layout
    [:div 
   [:p "The winner is: Player " winner]
   (board-html (model/get-board) false)
   [:div#score
       [:p "Player 1 Score: " (model/get-player-score \1)]
       [:p "Player 2 Score: " (model/get-player-score \2)]
       [:p#again (link-to "/" "Play Again!")]
     ]
   ]))

(defn draw-screen []
  (layout
    [:div
     [:p "It's a draw!"]
     (board-html (model/get-board) false)
     [:div#score
       [:p "Player 1 Score: " (model/get-player-score \1)]
       [:p "Player 2 Score: " (model/get-player-score \2)]
       [:p#again (link-to "/" "Play Again!")]
     ]
    ]
    ))