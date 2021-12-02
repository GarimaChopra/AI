#Assignment 6
#Due Date:18 October'2021
#Garima Chopra

#Question: Use the neural library in RStudio to perform classification on a data set from Kaggle Red Wine Quality.

#installing package needed 
#install.packages('neuralnet')
#install.packages('dplyr')
#install.packages('GGally')
#install.packages('mltools')
#install.packages('data.table')


#Adding libraries to use
library(corrgram)
library(ggplot2)
library(GGally)
library(caret)
library(mltools)
library(data.table)
library(neuralnet)

set.seed(144)
#Loading dataset
RedWineDS<- read.csv("https://raw.githubusercontent.com/GarimaChopra/AI/main/winequality-red.csv")


#first few data of the dataset
head(RedWineDS,n=5)

#last few data of the dataset
tail(RedWineDS, n = 5)

#shape of dataset
dim(RedWineDS) 

#Column names for dataset
names(RedWineDS)

#summary of dataset
summary(RedWineDS)

#analysing coorelation
round(cor(RedWineDS[, 1:12], use="pair"),2)

corrgram(RedWineDS,order=TRUE, lower.panel=panel.shade,
upper.panel=panel.pie, text.panel=panel.txt,
main="RedWineDS")

#pairplots
pairs(quality~., data=RedWineDS, col=RedWineDS$quality)


#defining MinMax function
normalize <- function(x, na.rm = TRUE) {
  return((x- min(x)) /(max(x)-min(x)))
}
normalise_RedWineDS <- as.data.frame(apply(RedWineDS, normalize))

#normalise dataset summary
summary(normalise_RedWineDS)

#dataframe view
head(normalise_RedWineDS)


#analysing quality Column
ggplot(normalise_RedWineDS  ,aes(x=quality)) + geom_bar()

#Creating a copy of the dataframe
Copy_RedWineDS <- normalise_RedWineDS

#categorising quality
CatQuality<- cut(Copy_RedWineDS$quality, breaks = c(0,0.5,2), labels = c("Normal","Good"), right = FALSE)


#dropping ring coloumn from the dataframe
Copy_RedWineDS= subset(Copy_RedWineDS, select = -c(quality))

#adding new category column for quality
Copy_RedWineDS$quality<- CatQuality

#summary
summary(Copy_RedWineDS)


#analysing quality Column
ggplot(Copy_RedWineDS  ,aes(x=quality)) + geom_bar()

#analysing new dataframe
str(Copy_RedWineDS)

#getting subset of  5 rows of classification label 'Normal'
subset(Copy_RedWineDS, quality== 'Normal')[1:9,]
#getting subset of  5 rows of classification label 'Good'
subset(Copy_RedWineDS, quality== 'Good')[1:9,]


#plotting boxplot
dev.off() 
df=subset(RedWineDS)
par(mar=c(5,4,1,1)) #more space for the labels
boxplot(df)


# Binarize the categorical output
Copy_RedWineDS<-cbind(Copy_RedWineDS, Copy_RedWineDS$quality == 'Normal')
Copy_RedWineDS <-cbind(Copy_RedWineDS, Copy_RedWineDS$quality== 'Good')
names(Copy_RedWineDS)[13] <-'Normal'
names(Copy_RedWineDS)[14] <-'Good'
summary(Copy_RedWineDS)

#Creating train set (80%-20%)
train_input <-Copy_RedWineDS[1:1279,1:14]
df=train_input



#Creating testcases (80%-20%)
test_cases <-Copy_RedWineDS[1279:1599,1:14]
summary(test_cases)

#plot neural network model
nn <- neuralnet(Normal+Good ~ fixed.acidity+volatile.acidity+citric.acid+residual.sugar+chlorides+free.sulfur.dioxide+total.sulfur.dioxide+density+pH+sulphates+alcohol,
                data=df,act.fct="tanh" , hidden=c(3))

plot(nn) 
print(nn)
nn$call
nn$weights
nn$err.fct

#nn on training set 
Predict_train <-compute(nn, df)$net.result
Predict_train

#nn on test set
predict_test<-compute(nn, test_cases)$net.result
predict_test

#converting binary column to category for confusion matrix
maxidx <-function(arr) {return(which(arr == max(arr)))}
#apply function maxidx to rows because thesecond argument = 1

# idx will contain the column number corresponding to the highest value: 1, 2 or 3> 
idx <-apply(Predict_train, c(1), maxidx)
idx1 <-apply(predict_test, c(1), maxidx)

# convert column numbers to names> 
prediction_train <-c('Normal', 'Good')[idx]
prediction_test <-c('Normal', 'Good')[idx1]

# compute the confusion matrix for training set
table(prediction_train, df$quality)


# compute the confusion matrix for test set
table(prediction_test,test_cases$quality)


