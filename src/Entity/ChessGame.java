/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.*;
import ADT.RandomList;
import ADT.RandomListInterface;
import Client.MainFrame;

/**
 *
 * @author Eugence
 */
public class ChessGame {
    
    private ChessBoard map;
    private ChessEngine chessEngine;
    private PlayerEngine playerEngine;

    public ChessGame(ChessBoard map) {
        this.map = map;
    }

    public ChessEngine getChessEngine() {
        return chessEngine;
    }

    public PlayerEngine getPlayerEngine() {
        return playerEngine;
    }

    public ChessBoard getMap() {
        return map;
    }
    
    
    public ListInterface<Chess> initDefaultChessLocation(int playerSeq) {
        ListInterface<Chess> chessList = new List();
        boolean isWhite = playerSeq == 0 ? true : false;
        /*Spawn pawn*/
        int pawnDefaultLocation = playerSeq == 0 ? 7 : 2;
        String pawnSymbol = playerSeq == 0 ? "\u2659" : "\u265F";
        for (int p = 1; p <= 8; p++) {
            chessList.add(new Pawn(pawnSymbol, isWhite, new Position(pawnDefaultLocation, p)));
        }

        int chessDefaultRow = playerSeq == 0 ? 8 : 1;

        /*Spawn Rook*/
        String rookSymbol = playerSeq == 0 ? "\u2656" : "\u265C";
        chessList.add(new Rook(rookSymbol, isWhite, new Position(chessDefaultRow, 1)));
        chessList.add(new Rook(rookSymbol, isWhite, new Position(chessDefaultRow, 8)));

        /*Spawn Knight*/
        String knightSymbol = playerSeq == 0 ? "\u2658" : "\u265E";
        chessList.add(new Knight(knightSymbol, isWhite, new Position(chessDefaultRow, 2)));
        chessList.add(new Knight(knightSymbol, isWhite, new Position(chessDefaultRow, 7)));

        /*Spawn Bishop*/
        String bishopSymbol = playerSeq == 0 ? "\u2657" : "\u265D";
        chessList.add(new Bishop(bishopSymbol, isWhite, new Position(chessDefaultRow, 3)));
        chessList.add(new Bishop(bishopSymbol, isWhite, new Position(chessDefaultRow, 6)));

        /*Spawn Queen*/
        String queenSymbol = playerSeq == 0 ? "\u2655" : "\u265b";
        chessList.add(new Queen(queenSymbol, isWhite, new Position(chessDefaultRow, 4)));

        /*Spawn King*/
        String kingSymbol = playerSeq == 0 ? "\u2654" : "\u265A";
        chessList.add(new King(kingSymbol, new Position(chessDefaultRow, 5), isWhite));
        return chessList;
    }
    
    public ListInterface<Player> initPlayer(String name1, String name2) {
        
        RandomListInterface<Player> randomPlayerSelector = new RandomList();
        
        //Initialize Player
        Player whitePlayer = new Player(name1, true, initDefaultChessLocation(0));
        Player blackPlayer = new Player(name2, false, initDefaultChessLocation(1));
        
        //Add entry
        randomPlayerSelector.add(whitePlayer);
        randomPlayerSelector.add(blackPlayer);
        
        ListInterface<Player> playerList = new List();
        
        //Random choose first player
        playerList.add(randomPlayerSelector.pickRandomEntry());
        //Add remaining player
        playerList.add(randomPlayerSelector.getEntry(randomPlayerSelector.getLength()));
        
        return playerList;
    }
    
    public void activateGame(String playerName1, String playerName2) {
        
        //Init player and chess
        ListInterface<Player> playerList = initPlayer(playerName1,playerName2);
        playerEngine = new PlayerEngine(playerList);
        chessEngine = new ChessEngine(playerList);
        
        //Display Chess into UI chess board
        for(int i = 1; i <= playerList.getLength();i++){
            Player p = playerList.getEntry(i);
            map.initLocation(p.getChessList());
        }
        
        proceedNextRound();
    }
    
    public void proceedNextRound() {
        ListInterface<Player> playerList = playerEngine.getPlayerList();

        ListInterface<Chess> chessList = new List();
        if (playerEngine.getCurrentPlayer() != null) {
            chessList = playerEngine.getCurrentPlayer().getChessList();

        } else {
            chessList.addAll(playerList.getEntry(1).getChessList());
            chessList.addAll(playerList.getEntry(2).getChessList());
        }
        map.disableListener(chessList);

        Player currentPlayer = playerEngine.nextRound();
        map.addNewMessage(currentPlayer.getName() + " 's Turn.");
        map.splitLine();
        
        for(int i = 1; i <= currentPlayer.getChessList().getLength();i++){
            Chess chess = currentPlayer.getChessList().getEntry(i);
            map.addListener(chess,chessEngine.getLabelMouseListener(chess,this));
        }
        MainFrame.refreshFrame();
    }
    
    public Chess getLastChess(boolean isWhite) {
        return chessEngine.getLastMoveChess(isWhite);
    }

    public void setLastChess(Chess chess, boolean isWhite) {
        chessEngine.setLastMoveChess(chess, isWhite);
    }
    
    public Player getCurrentPlayer() {
        return playerEngine.getCurrentPlayer();
    }

    public Player getNextPlayer() {
        return playerEngine.getNextPlayer();
    }
}
