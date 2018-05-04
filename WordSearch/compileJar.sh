javac src/*
mv src/WordSearch.class .
jar cvfe WordSearch.jar WordSearch WordSearch.class 
rm WordSearch.class
