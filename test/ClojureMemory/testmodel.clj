(ns ClojureMemory.testmodel

  (:use ClojureMemory.model)
  (:use clojure.test)
  (:require [ClojureMemory.testdata :as td]))

(deftest get-board-cell-test
  (let [testboard [[\A \B \D \E \C]
							     [\A \B \C \F \G]
							     [\D \G \H \J \I]
							     [\E \F \I \J \H]]]
    (is (= (get-board-cell testboard 0 0) \A))
    (is (= (get-board-cell testboard 0 1) \B))
    (is (= (get-board-cell testboard 1 1) \B))
    (is (= (get-board-cell testboard 2 3) \J)))
)

(deftest full-board-test
  (is (full-board? (td/full-board)) true)
  (is (full-board? (td/non-full-board)) false)
)

(deftest checkIfEqualTest
  (binding [noir.session/*noir-session* (atom {})]
  (is (checkIfEqual? \1 (get-board-cell (td/combination) 0 0) (get-board-cell (td/combination) 1 0)) true)
  (is (checkIfEqual? \1 (get-board-cell (td/combination) 0 0) (get-board-cell (td/combination) 0 1)) false)
  )
)