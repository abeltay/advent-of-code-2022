value=$1

echo "$value" > day.txt
touch src/Day"$value"_test.txt
sed "s/{replace}/$value/g" src/template_part1.kt > src/Day"$value"_part1.kt
sed "s/{replace}/$value/g" src/template_part2.kt > src/Day"$value"_part2.kt
