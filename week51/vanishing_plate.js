function solution(board, aloc, bloc) {
  const n = board.length;
  const m = board[0].length;

  const dr = [-1, 1, 0, 0];
  const dc = [0, 0, -1, 1];

  let state = 0;

  // board를 bit로 변환
  for (let r = 0; r < n; r++) {
    for (let c = 0; c < m; c++) {
      if (board[r][c] === 1) {
        state |= 1 << (r * m + c);
      }
    }
  }

  const isBoard = (state, r, c) => {
    if (r < 0 || r >= n || c < 0 || c >= m) return false;

    let idx = r * m + c;

    return (state & (1 << idx)) !== 0;
  };

  // 현재 플레이어 위치, 상대 위치, board 상태
  // return [현재 플레이어 승리 여부, 남은 이동 횟수]
  const dfs = (cur, other, state) => {
    let [r, c] = cur;

    // 내가 서 있는 발판이 이미 사라진 경우
    if (!isBoard(state, r, c)) {
      return [false, 0];
    }

    let canMove = false;

    let win = Infinity;
    let lose = 0;

    for (let d = 0; d < 4; d++) {
      let nr = r + dr[d];
      let nc = c + dc[d];

      if (!isBoard(state, nr, nc)) continue;

      canMove = true;

      // 현재 위치 발판 제거
      let idx = r * m + c;
      let nextState = state & ~(1 << idx);

      // 턴 교체
      let [otherWin, count] = dfs(other, [nr, nc], nextState);

      // 상대가 지는 경우
      // -> 나는 이길 수 있음
      if (!otherWin) {
        win = Math.min(win, count + 1);
      }

      // 상대가 이기는 경우
      // -> 나는 지는 경우
      else {
        lose = Math.max(lose, count + 1);
      }
    }

    // 이동 자체가 불가능
    if (!canMove) {
      return [false, 0];
    }

    // 하나라도 이길 수 있는 경우가 있으면
    // 가장 빨리 이기는 경우 선택
    if (win !== Infinity) {
      return [true, win];
    }

    // 전부 지는 경우
    // 최대한 오래 버티기
    return [false, lose];
  };

  return dfs(aloc, bloc, state)[1];
}
