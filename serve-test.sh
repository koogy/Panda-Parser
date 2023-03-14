input=1
run ()
{
  time ./panda-parser serve --input-dir "../exemples/Big-files-test/$input" --output-dir "out_$input"
}
input=1
run;