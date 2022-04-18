package com.unicepta.minesweeper.api;

import com.unicepta.minesweeper.exception.LoseException;
import com.unicepta.minesweeper.dto.BoardMapper;
import com.unicepta.minesweeper.dto.BoardResponse;
import com.unicepta.minesweeper.dto.CreateGameRequest;
import com.unicepta.minesweeper.dto.UncoverTileRequest;
import com.unicepta.minesweeper.model.Board;
import com.unicepta.minesweeper.service.GameService;
import com.unicepta.minesweeper.service.SessionService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/games")
public class Game {

    private final SessionService sessionService = new SessionService();
    private final GameService gameService = new GameService();

    private final BoardMapper boardMapper = new BoardMapper();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public BoardResponse createGame(final CreateGameRequest request) {
        final var createdBoard = gameService.buildNewBoard(request.width, request.height, request.numberOfMines);
        sessionService.addNewBoard(createdBoard);
        return boardMapper.mapResponse(createdBoard);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public BoardResponse uncoverTile(final UncoverTileRequest request) {
        Board updatedBoard;
        try {
            final var board = sessionService.getBoard(request.boardId);
            updatedBoard = gameService.markTile(board, request.x, request.y);
        } catch (final LoseException exception) {
            /*
               It's kind of exception mapper
               I would prefer such instruments as ExceptionHandler in Spring if I have enough time
             */
            updatedBoard = exception.getBoard();
            sessionService.deleteBoard(updatedBoard.getId());
        }
        return boardMapper.mapResponse(updatedBoard);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public BoardResponse getBoard(@PathParam("id") final String id) {
        final var board = sessionService.getBoard(id);
        return boardMapper.mapResponse(board);
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public BoardResponse deleteBoard(@PathParam("id") final String id) {
        final var board = sessionService.deleteBoard(id);
        return boardMapper.mapResponse(board);
    }
}
