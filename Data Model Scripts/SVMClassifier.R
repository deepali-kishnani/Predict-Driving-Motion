library(e1071)
library(ROCR)
library(sqldf)
library(caret)

library(data.table) 

setwd("C:\\Users\\Lenovo\\Desktop\\IIIT-Delhi\\semester 7\\IP_LOGS")
sample70 <- read.csv("trainingdata2.csv", stringsAsFactors=F)
sample30<- read.csv("data-train.csv", stringsAsFactors=F)
train <- sample70[1:2]
test <- sample30

svm_model <- svm(as.factor(target)~., type='C', kernel= 'linear', data = train, probability=TRUE)
save(svm_model,file="svm_model_equal.Rdata")

pred <- predict (svm_model, test, probability=TRUE, decision.values = FALSE)
svm_predict_prob<-attr (pred, "probabilities")
pred2 <- prediction(svm_predict_prob[,1],sample30$target)
perf <- performance(pred2,"tpr","fpr")
plot(perf)

svm_predict<-predict (svm_model, test, probability=FALSE,decision.values=FALSE)

svm_confMatrix<-confusionMatrix(data = svm_predict,reference = sample30$target)
