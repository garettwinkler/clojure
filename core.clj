(ns clojure-noob.core
  (:gen-class))

(defn -main
  [& args]
  (println "Testing/learning clojure"))


;basics

(defn train
  []
  (println "Choo Choo!"))

(+ 1 (* 2 3) 4)

(defn testConditionals
  []
  (if true
    "print something true"
    "print something false"))

(defn doExample
  []
  (if true
    (do (println "Success!")
        "By Zeus's hammer!")
    (do (println "Failure!") 
        "By Aquaman's trident!")))

(defn whenExample
  []
  (when true
    (println "Success!")
    "abra cadabra"))

;defining a variable
(def failedProtagonistNames
  ["Larry Potter" "Doreen the Explorer"])

(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED!"
         "DOOOOOOOMED!")))

;maps
{:first-name "Charlie"
 :last-name "McFishwich"}

(get {:a 0 :b 1} :b)
; => 1

(get {:a 0 :b {:c "ho hum"}} :b)
; => {:c "ho hum"}

(get {:a 0 :b 1} :c)
; => nil

(get {:a 0 :b 1} :c "unicorns?")
; => "unicorns?"


;keywords
;can be used as functions (basically does get?
(:a {:a 1 :b 2 :c 3})
; => 1

;can provide a default value
(:d {:a 1 :b 2 :c 3} "No gnome knows homes like Noah knows")
; => "No gnome knows homes like Noah knows"

;nested maps 
;use get-in
(def m {:username "sally"
        :profile {:name "Sally Clojurian"
                  :address {:city "Austin" :state "TX"}}})

(get-in m [:profile :name])
;"Sally Clojurian"
(get-in m [:profile :address :city])
;"Austin"
(get-in m [:profile :address :zip-code])
;nil
(get-in m [:profile :address :zip-code] "no zip code!")
;"no zip code!"



;vectors
;are collections, conj adds element to end
(conj [1 2 3] 4)
; => [1 2 3 4]

;lists
;also collections, conj adds element to start
(conj '(1 2 3) 4)
; => (4 1 2 3)

;sets
;collection unique values
;hash sets and sorted sets
(hash-set 1 1 2 2)
; => #{1 2}

(conj #{:a :b} :b)
; => #{:a :b}

(set [3 3 3 4 4])
; => #{3 4}


;functions
;example function with different airty overloading and simple recursion
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
     (str "I " chop-type " chop " name "! Take that!"))
  ([name]
     (x-chop name "karate")))


;"rest" parameter example
;parameters become list
(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!" ))

(defn codger
    [& whippersnappers]
    (map codger-communication whippersnappers))
;>>get off my lawn person1
;>>get off my lawn person2
;etc

(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))

;(favorite-things "Doreen" "gum" "shoes" "kara-te")


;destructuring
;binding names to values within a collection
;parameter is inside a vector
;you can also destructure maps (check syntax)
(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))

;example call
;(chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])


;anon functions

(map (fn [name] (str "Hi, " name))
      ["Darth Vader" "Mr. Magoo"])
; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

((fn [x] (* x 3)) 8)
; => 24

;but you can name anon functions
(def my-special-multiplier (fn [x] (* x 3)))

;shorthand anon functions
(#(* % 3) 8)

(map #(str "Hi, " %)
     ["Darth Vader" "Mr. Magoo"])

(#(str %1 " and " %2) "cornbread" "butter beans")
; => "cornbread and butter beans"

(#(identity %&) 1 "blarg" :yip)
; => (1 "blarg" :yip)


;returning functions

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))



;let
(def x 0)
(let [x 1] x)
; => 1
;x has a new scope

(def x 0)
(let [x (inc x)] x)
; => 1

(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])
(let [dalmatians (take 2 dalmatian-list)]
  dalmatians)

(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])
; => ["Pongo" ("Perdita" "Puppy 1" "Puppy 2")]



;loop
(println "Testing a loop")
(println)

(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))
; => Iteration 0
; => Iteration 1
; => Iteration 2
; => Iteration 3
; => Iteration 4
; => Goodbye!

;function equivalent of above loop
(defn recursive-printer
  ([]
     (recursive-printer 0))
  ([iteration]
     (println iteration)
     (if (> iteration 3)
       (println "Goodbye!")
       (recursive-printer (inc iteration)))))

; => Iteration 0
; => Iteration 1
; => Iteration 2
; => Iteration 3
; => Iteration 4
; => Goodbye!


;Regualar Expressions
;Starts with # then quotes.
;^ indicates beginning only
(re-find #"^left-" "left-eye")
; => "left-"

(re-find #"^left-" "cleft-chin")
; => nil

(re-find #"^left-" "wongleblart")
; => nil

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(matching-part {:name "left-eye" :size 1})
; => {:name "right-eye" :size 1}]

(matching-part {:name "head" :size 3})
; => {:name "head" :size 3}]

;testing
(defn matching-part2
  [sometext]
  {:name (clojure.string/replace (:name sometext) #"where" "some")
   :size (:size sometext)})

(matching-part2 {:name "where-eye wherefor somewhere whereto wherefor" :size 1})
; => {:name "some-eye somefor somesome someto somefor", :size 1}


;reduce
;; sum with reduce
(reduce + [1 2 3 4])
; => 10


(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
      (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

;sample run
(hit asym-hobbit-body-parts)



;exercises

(defn addHunnid
  [num]
  (+ num 100))


(defn dec-maker
  "creating a custom decrementor"
  [number]
  #(- % number))

(def dec5 (dec-maker 5))

(defn mapset
  "works like map but returns a set"
  [function vector]
  (map function (set vector)))



;programming to abstraction examples
;same behavior throughout
(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(map titleize ["Hamsters" "Ragnarok"])
; => ("Hamsters for the Brave and True" "Ragnarok for the Brave and True")

(map titleize '("Empathy" "Decorating"))
; => ("Empathy for the Brave and True" "Decorating for the Brave and True")

(map titleize #{"Elbows" "Soap Carving"})
; => ("Elbows for the Brave and True" "Soap Carving for the Brave and True")

(map #(titleize (second %)) {:uncomfortable-thing "Winking"})
; => ("Winking for the Brave and True")

(map #(titleize (first %)) {:uncomfortable-thing "Winking"})
; => ("Winking for the Brave and True")

;??? is that not a map? key value pair? first is the pair, there is no second. Missed something


(defn testingLoop [a]
  (loop [[part & rest] a]
    (if (= part nil)
      (println "done")
      (do (println part)
          (testingLoop rest))))) ;can use recur or testingLoop here. Is there a difference?

(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))


;4clojure exercises

;Write a function which takes two sequences and returns the first item from each, then the second item from each, then the third, etc.
(defn myInterleave [a b]
  (loop [[part1 & rest1] a
         [part2 & rest2] b
         interleaved []]
    (if (or (= part1 nil) (= part2 nil))
      (println interleaved)
      (recur rest1 rest2 (conj interleaved part1 part2)))))

;better shorter solution
; #(flatten (map vector %1 %2)) 


;Write a function that replicates each element of a seq a variable number of times

(defn myReplicate [coll num]
  (map repeat (repeat (count coll) num) coll))

(defn myReplicate2 [coll num]
  (flatten (map repeat (repeat (count coll) num) coll)))

;do 3rd one
(defn myReplicate3 [coll num]
  (concat (map repeat (repeat (count coll) num) coll)))

;map
;you can use map with a collection of functions
;ex
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])
; => (17 3 17/3)

(stats [80 1 44 13 6])
; => (144 5 144/5)

;also used with the keyword of map ds. ex
(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")



(map + [1 2 3] (iterate inc 1))
;is adding 1 2 3 to 1 2 3 4 5..... runs out at 3
;;=> (2 4 6)

;reminder how reduce and map are different
;reduce requires two arguments. Takes initial and first or first and second if no initial provided

;map is applying a function to the first element of given collections. Can be any number of collections and therefore arguments
;if it's (map fn coll) with only 1 collection is just applying the function to each element and you get result collection. Most common use case?

;Write a function which separates the items of a seq by an arbitrary value
(defn myInterpose [value coll]
  (rest (mapcat #(list value %) coll)))



;reduce examples
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})
; => {:max 31, :min 11}

;above is equivalent to below
(assoc (assoc {} :max (inc 30))
       :min (inc 10))

;assoc takes 3 args. a map, key, and value and makes a map.


;take, drop, takewhile and dropwhile
(take 3 [1 2 3 4 5 6 7 8 9 10])
; => (1 2 3)

(drop 3 [1 2 3 4 5 6 7 8 9 10])
; => (4 5 6 7 8 9 10)

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #(< (:month %) 3) food-journal)
; => ({:month 1 :day 1 :human 5.3 :critter 2.3}
;      {:month 1 :day 2 :human 5.1 :critter 2.0}
 ;     {:month 2 :day 1 :human 4.9 :critter 2.1}
  ;    {:month 2 :day 2 :human 5.0 :critter 2.5})

(take-while #(<= % 4) (iterate inc 1))

;same idea with drop-while
(drop-while #(< (:month %) 3) food-journal)
; => ({:month 3 :day 1 :human 4.2 :critter 3.3}
;      {:month 3 :day 2 :human 4.0 :critter 3.8}
;      {:month 4 :day 1 :human 3.7 :critter 3.9}
;      {:month 4 :day 2 :human 3.7 :critter 3.6})

(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
;      {:month 2 :day 2 :human 5.0 :critter 2.5}
;      {:month 3 :day 1 :human 4.2 :critter 3.3}
;      {:month 3 :day 2 :human 4.0 :critter 3.8})


;filter can accomplish the same things
(filter #(< (:human %) 5) food-journal)
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
;      {:month 3 :day 1 :human 4.2 :critter 3.3}
 ;     {:month 3 :day 2 :human 4.0 :critter 3.8}
  ;    {:month 4 :day 1 :human 3.7 :critter 3.9}
   ;   {:month 4 :day 2 :human 3.7 :critter 3.6})

(filter #(< (:month %) 3) food-journal)
; => ({:month 1 :day 1 :human 5.3 :critter 2.3}
;      {:month 1 :day 2 :human 5.1 :critter 2.0}
 ;     {:month 2 :day 1 :human 4.9 :critter 2.1}
  ;    {:month 2 :day 2 :human 5.0 :critter 2.5})

;filter goes through everything though. So drop or take while can be more efficient if we know it can get what we want. filter is going to look at every element no matter what

;some = want to know whether a collection contains any values that are true for a predicate function.
(some #(> (:critter %) 5) food-journal)
; => nil

(some #(> (:critter %) 3) food-journal)
; => true

(some #(and (> (:critter %) 3) %) food-journal)
; => {:month 3 :day 1 :human 4.2 :critter 3.3}

;to get the actual first entry that meets condition


;sort and sort-by
(sort [3 1 2])
; 1 2 3

;sort-by you apply a key function to sort by
(sort-by count ["aaa" "c" "bb"])
; => ("c" "bb" "aaa")

(concat [1 2] [3 4])
; [1 2 3 4]



;write a function that packs consecutive duplicates into sublists
(defn packConsec [sequ]
  (partition-by identity sequ))
;partition by takes a function/condition like odd? or #(> 3 %) and splits everything up by the results

;write a funciton that drops every nth item from a sequence
(defn dropNth [sequ n]
   (keep-indexed #(if (not= (mod %1 n) (dec n)) %2) sequ))
;keep-indexed returns lazy seq of non nil results of (f index item) of the sequence given





;lazy seqs

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

;see time examples

; Map hasn't actually mapped anything until attempt to access. After it is realized then it is much faster. Also clojure chunks its lazy seq so instead of realizing only 1 element (first) it does the next 31 too. Usually results in increased performance



;infinite sequences
(concat (take 8 (repeat "na")) ["Batman!"])
; => ("na" "na" "na" "na" "na" "na" "na" "na" "Batman!")

(take 3 (repeatedly (fn [] (rand-int 10))))
;ex
; => (1 4 0) ;

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 10 (even-numbers))
; => (0 2 4 6 8 10 12 14 16 18)

;so here the elements are not realized until take comes to get them
