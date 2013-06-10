(ns ClojureMemory.testdata
 (:use clojure.set))

(defn full-board []
 #{ [[\A \B \D \E \C]
     [\A \B \C \F \G]
     [\D \G \H \J \I]
     [\E \F \I \J \H]]
   }
)

(defn non-full-board []
   #{ [[\A \B \D \E \C]
     [\A \B \C \F \G]
     [\D \G \H \J \I]
     [\E \F \I \J \-]]
   }
)

(defn combination []
  #{ [[\A \- \- \- \-]
      [\A \- \- \- \-]
      [\- \- \- \- \-]
      [\- \- \- \- \-]]
  }
)

(defn wrong-combination []
  #{ [[\A \B \- \- \-]
      [\- \- \- \- \-]
      [\- \- \- \- \-]
      [\- \- \- \- \-]]
  }
)