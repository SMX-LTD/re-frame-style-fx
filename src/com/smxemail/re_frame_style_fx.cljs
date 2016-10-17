(ns com.smxemail.re-frame-style-fx
  (:require
    [cljs.spec :as s]
    [goog.style]
    [re-frame.core :refer [console dispatch reg-fx]]))

(s/def ::sequential-or-map (s/or :list-or-vector sequential? :map map?))

;; An effects handler that actions installing a style sheet.
;;
;; To install a style sheet supply a map or vector of maps using the following
;; options:
;;   :content The style sheet content (i.e CSS).
;;   :on-success
;;   :on-failure
;;
(reg-fx
  :style/install
  (fn style-install-effect [options]
    (when (= :cljs.spec/invalid (s/conform ::sequential-or-map options))
      (console :error (s/explain-str ::sequential-or-map options)))
    (cond
      (sequential? options)
      (run! style-install-effect options)
      (map? options)
      (let [{:keys [content on-success on-failure]
             :or   {on-success [:style-install-no-on-success]
                    on-failure [:style-install-no-on-failure]}} options]
        (try
          (let [element (goog.style/installStyles content)]
            (dispatch (conj on-success options element)))
          (catch :default e
            (dispatch (conj on-failure e))))))))

;; An effects handler that actions uninstalling a style sheet.
;;
;; To uninstall a style sheet supply a map or vector of maps using the following
;; options:
;;   :element The style element that was created by :style/install
;;   :on-success
;;   :on-failure
;;
(reg-fx
  :style/uninstall
  (fn style-uninstall-effect [options]
    (when (= :cljs.spec/invalid (s/conform ::sequential-or-map options))
      (console :error (s/explain-str ::sequential-or-map options)))
    (cond
      (sequential? options)
      (run! style-uninstall-effect options)
      (map? options)
      (let [{:keys [element on-success on-failure]
             :or   {on-success [:style-uninstall-no-on-success]
                    on-failure [:style-uninstall-no-on-failure]}} options]
        (try
          (goog.style/uninstallStyles element)
          (dispatch (conj on-success options element))
          (catch :default e
            (dispatch (conj on-failure e))))))))
