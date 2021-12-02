#Assignment 5
#Due Date:11 October'2021
#Garima Chopra

#Question:Import the CSV training data into R
#Use C5.0 to create a rule-based model and compute its accuracy on the training data set (the concept to predict is Rings)

#installing package needed 
#install.packages('C50', dependencies = T)
#install.packages("corrgram")
#install.packages('caret')

#Adding libraries to use
library(corrgram)
library(C50)
library(ggplot2)
library(caret)

#Loading dataset
abaloneDS<- read.csv("https://raw.githubusercontent.com/GarimaChopra/AI/main/abalone.csv")

#first few data of the dataset
head(abaloneDS,n=5)

#last few data of the dataset
tail(abaloneDS, n = 5)

#shape of dataset
dim(abaloneDS) 

#Column names for dataset
names(abaloneDS)

#summary of dataset
summary(abaloneDS)

#analysing data frame
str(abaloneDS)

#analysing coorelation
round(cor(abaloneDS[, 2:9], use="pair"),2)

corrgram(Copy_abalone,order=TRUE, lower.panel=panel.shade,
         upper.panel=panel.pie, text.panel=panel.txt,
         main="abaloneDS")

#analysing Rings Column
ggplot(abaloneDS, aes(x=Rings)) + geom_bar()

#cut the ring in some class before converting it to categorical data
#we will create ring category Class A <= 11 and class B < 11
CatRings <- cut(abaloneDS$Rings, breaks = c(0,6,13,30), labels = c("A","B","C"), right = FALSE)

#category for 10 rows
CatRings[1:10]

#analysing CatRings Column
ggplot(abaloneDS, aes(x=CatRings)) + geom_bar()

#Creating a copy of the dataframe
Copy_abalone <- abaloneDS

#dropping ring coloumn from the dataframe
Copy_abalone= subset(Copy_abalone, select = -c(Rings))


#adding the categorical ring coloumn to the table
Copy_abalone$Rings <- CatRings

#dataframe view
head(Copy_abalone)


#analysing new dataframe
str(Copy_abalone)
#getting subset of 5 rows of classification label 'A'
subset(Copy_abalone, Rings == 'A')[1:9,]
#getting subset of  5 rows of classification label 'B'
subset(Copy_abalone, Rings == 'B')[1:9,]
#getting subset of  5 rows of classification label 'C'
subset(Copy_abalone, Rings == 'C')[1:9,]



#plotting boxplot
df=subset(Copy_abalone, select = -c(Sex))
par(mar= c(7,5,1,1)) #more space for the labels
boxplot(df)


#Classification on abalone dataset
#creating a training testing set of 70%-30% ratio

#Creating train set
train_input <-Copy_abalone[1:2923,1:8]
summary(train_input)
train_output <-Copy_abalone[1:2923,9]
summary(train_output)


#Plotting unpruned decision tree for training data
train_model1 <-C5.0(train_input, train_output, control = C5.0Control(noGlobalPruning = TRUE,minCases=1))
plot(train_model1, main="C5.0 Decision Tree -Unpruned, min=1")

#summary for unpruned tree
summary(train_model1)

#Plotting pruned decision tree for train dataset
train_model2 <-C5.0(train_input, train_output, control = C5.0Control(noGlobalPruning = FALSE))
plot(train_model2, main="C5.0 Decision Tree -Pruned")

#summary for pruned tree
summary(train_model2)


#Creating testcases
test_cases <-Copy_abalone[2924:4177,1:9]
summary(test_cases)

#creating train cases
train_cases <-Copy_abalone[1:2923,1:9]
summary(train_cases)

#predicting the train cases
train_pred<-predict(train_model2, train_cases, type="class")
train_pred

#predicting test cases
test_pred<-predict(train_model2, test_cases, type="class")
test_pred

#creating rules on traing set
rules_model <-C5.0(train_input, train_output, rules=TRUE)
summary(rules_model)

#Accuracy Test on Training Data
confMat <- table(train_cases$Rings,train_pred)
accuracy_Test <- (sum(diag(confMat)) / sum(confMat))*100
print(paste('Accuracy for training data is:', accuracy_Test))

#Accuracy Test on Testing Data
confMat1 <- table(test_cases$Rings,test_pred)
accuracy_Test1 <- (sum(diag(confMat1)) / sum(confMat1))*100
print(paste('Accuracy for testing data is:', accuracy_Test1))


#Creates vectors having data points
expected_value <- test_cases$Rings
predicted_value <- test_pred

#Creating confusion matrix
example <- confusionMatrix(data=predicted_value, reference = expected_value)
example

drop 