(defproject trainspotter/trainspotter "0.1.0-SNAPSHOT"
  :description "FIXME: Android project description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :global-vars {clojure.core/*warn-on-reflection* true}

  :source-paths ["src/clojure" "src"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :plugins [[lein-droid "0.4.6"]
            [cider/cider-nrepl "0.8.2"]]

  :dependencies [[org.clojure-android/clojure "1.7.0-r4"]
                 [neko/neko "4.0.0-alpha5"]
                 [cheshire "5.6.3"]
                 [clj-http-lite "0.3.0"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [clj-time "0.11.0"]]
  :profiles {:default [:dev]

             :repl
             [{:dependencies [[org.clojure/tools.nrepl "0.2.10"]]}]

             :local-testing
             [:repl
              {:android {:aot :all-with-unused}
               :target-path "target/local-testing"
               :dependencies [[org.robolectric/robolectric "3.0"]
                              [org.clojure-android/droid-test "0.1.1-SNAPSHOT"]]}]

             :dev
             [:repl
              {:target-path "target/debug"
               :android {:aot :all-with-unused
                         :manifest-options {:app-name "TrainSpotter (debug)"}
                         ;; Uncomment to be able install debug and release side-by-side.
                         ;; :rename-manifest-package "org.trainspotter.debug"
                         }}]
             :release
             [{:target-path "target/release"
               :android
               {;; :keystore-path "/home/user/.android/private.keystore"
                ;; :key-alias "mykeyalias"
                ;; :sigalg "MD5withRSA"

                :use-debug-keystore true
                :ignore-log-priority [:debug :verbose]
                :aot :all
                :build-type :release}}]

             :lean
             [:release
              {:dependencies ^:replace [[org.skummet/clojure "1.7.0-r2"]
                                        [neko/neko "4.0.0-alpha5"]]
               :exclusions [[org.clojure/clojure]
                            [org.clojure-android/clojure]]
               :jvm-opts ["-Dclojure.compile.ignore-lean-classes=true"]
               :android {:lean-compile true
                         :proguard-execute true
                         :proguard-conf-path "build/proguard-minify.cfg"}}]}

  :android {;; Specify the path to the Android SDK directory.
            ;; :sdk-path "/home/user/path/to/android-sdk/"
            :sdk-path "../sdk/android-sdk-linux/"

            ;; Increase this value if dexer fails with OutOfMemoryException.
            :dex-opts ["-JXmx4096M" "--incremental"]

            :target-version "22"
            :aot-exclude-ns ["clojure.parallel" "clojure.core.reducers"
                             "cider.nrepl" "cider-nrepl.plugin"
                             "cider.nrepl.middleware.util.java.parser"
                             #"cljs-tooling\..+"]})
