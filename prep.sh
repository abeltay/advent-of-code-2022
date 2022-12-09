value=$1

echo "$value" > day.txt
touch src/Day"$value"_test.txt
sed "s/{replace}/$value/g" src/template.kt > src/Day"$value".kt
