package solution;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import resources.classes.Expr;

public class ExprGui extends Application {
    Label errorLabel;
    String userInput;
    Canvas canvas = new Canvas(400,400);


    public void start(Stage primaryStage){
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        root.setPrefWidth(canvas.getWidth()+220);
        root.setStyle("-fx-background-color: ivory");

        Label formulaInputLabel = new Label("Type the formula you want to evaluate into the box below.");
        formulaInputLabel.setPrefWidth(200);
        formulaInputLabel.setWrapText(true);
        formulaInputLabel.setStyle("-fx-text-fill: black; -fx-font-weight:bold; -fx-font-size:15px");
        formulaInputLabel.relocate(canvas.getWidth()+10, 20);
        
        errorLabel = new Label("");
        errorLabel.setPrefWidth(200);
        errorLabel.setWrapText(true);
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight:bold; -fx-font-size:15px");
        errorLabel.relocate(canvas.getWidth()+10, 200);

        TextField formulaField = new TextField();
        formulaField.setPrefWidth(200);
        formulaField.relocate(canvas.getWidth()+10, 100);


        Button enterButton = new Button("Enter Data");
        enterButton.relocate(canvas.getWidth()+70, canvas.getHeight()-40);
        enterButton.setDefaultButton(true);
        enterButton.setOnAction(evt ->{
            userInput =  formulaField.getText();
            onEnterButtonClicked();
        });

        root.getChildren().add(enterButton);
        root.getChildren().add(formulaField);
        root.getChildren().add(errorLabel);
        root.getChildren().add(formulaInputLabel);


        drawCanvas();
        primaryStage.show();
    }

    private void onEnterButtonClicked(){
        errorLabel.setText("");
        Expr expression;
        drawCanvas();
        try{
            expression = new Expr(userInput);
        }catch (IllegalArgumentException e){
            errorLabel.setText(e.getMessage());
            return;
        }
        drawLine(expression);
    }

    private void drawCanvas (){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.IVORY);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

        int hOffSet = 80;
        int vOffSet = 10;
        int planeSize = 300;
        int minorTickSize = 10;
        int majorTickSize = 20;


        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(hOffSet,vOffSet,planeSize,planeSize);

        graphicsContext.setStroke(Color.GRAY);
        for(int i = 0; i<11; i++){
            graphicsContext.strokeLine(hOffSet+i*(planeSize/10.0),planeSize+vOffSet,hOffSet+i*(planeSize/10.0), planeSize+vOffSet+minorTickSize);
            graphicsContext.strokeLine(hOffSet,i*(planeSize/10.0)+vOffSet,hOffSet-minorTickSize,i*(planeSize/10.0)+vOffSet);
        }

        graphicsContext.setLineWidth(2);
        graphicsContext.strokeLine(hOffSet-majorTickSize, planeSize/2.0+vOffSet, hOffSet+planeSize, planeSize/2.0+vOffSet);
        graphicsContext.strokeLine(hOffSet+planeSize/2.0, vOffSet, hOffSet+planeSize/2.0, planeSize+vOffSet+majorTickSize);

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(Font.font("", 15));
        for(int i = -5; i<6; i++){
            graphicsContext.fillText(String.valueOf(i), hOffSet-minorTickSize*3, (i+5)*(planeSize/10.0)+vOffSet+5);
            graphicsContext.fillText(String.valueOf(i), (i+5)*(planeSize/10.0)+hOffSet-5,planeSize+ vOffSet+ minorTickSize*3);
        }
        graphicsContext.fillText("Y axis", planeSize/2.0+hOffSet-20,vOffSet*6+planeSize);
        graphicsContext.rotate(-90);
        graphicsContext.fillText("X axis", -(planeSize/2.0+30),vOffSet*4);
        graphicsContext.rotate(90);



    }

    private void drawLine(Expr expression){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(Color.BLACK);

        double[]yVals = new double[302];
        double[]xVals = new double[302];
        double xVal = -5;
        for (int i = 0; i<xVals.length; i++){
            xVals[i] = xVal+i*(10.0/301);
            yVals[i]=expression.value(xVals[i]);
        }
        double width = 300;
        double height = 300;

        int hOffset = 80;
        int vOffset = 10;
        double prevHorizontal = ((xVals[0] + 5)/10 * width)+hOffset;
        double prevVertical  = ( (5 - yVals[0])/10 * height)+vOffset;
        double horizontal;
        double vertical;



        for(int i = 1; i<xVals.length; i++){
            if(Double.isNaN(yVals[i])){
                while(Double.isNaN(yVals[i])){
                    i++;
                }
                horizontal = ((xVals[i] + 5)/10 * width)+hOffset;
                vertical  = ( (5 - yVals[i])/10 * width)+vOffset;
                if(Double.isNaN(yVals[i+1])){
                    graphicsContext.fillOval(horizontal,vertical,horizontal,vertical);
                }else{
                    prevHorizontal = horizontal;
                    prevVertical = vertical;
                }
                continue;
            }

            horizontal = ((xVals[i] + 5)/10 * width)+hOffset;
            vertical  = ( (5 - yVals[i])/10 * height)+vOffset;

            if(vertical>vOffset-1 && prevVertical>vOffset-1&&vertical<height+vOffset+1&&prevVertical<height+vOffset+1){
                graphicsContext.strokeLine(prevHorizontal,prevVertical, horizontal,vertical);
            }
            prevHorizontal = horizontal;
            prevVertical = vertical;
        }

    }

    public static void main (String[]Args){
        launch(Args);
    }
}
