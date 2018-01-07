package webGui.controllers;

import core.guiUtilities.ServerMessageHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import webGui.util.WebGuiController;

public class WebGuiShellController extends WebGuiController implements ServerMessageHandler {

	@FXML
	private AnchorPane webGuiMainViewRegion;

	@Override
	public void handleServerMessage(String msg) {

	}
}
