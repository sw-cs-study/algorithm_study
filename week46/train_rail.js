function solution(grid) {
  const n = grid.length;
  const m = grid[0].length;
  var answer = 0;

  const delta = [
    [0, 1],
    [1, 0],
    [0, -1],
    [-1, 0],
  ];
  // 이미 놓여진 선로 파악해서 끝점 도달 시 선로를 다 지나갔는지 체크
  let cnt = 0;
  grid.forEach((line, i) => {
    line.forEach((v, j) => {
      if (v === 3) cnt += 2;
      else if (v > 0) cnt++;
    });
  });

  // 현재 방향에서 선로로 진입가능한지
  const canPass = (d, rail) => {
    let ret;
    switch (d) {
      case 0:
        if (rail === 1 || rail === 3 || rail === 4 || rail === 7) return true;
        break;
      case 1:
        if (rail === 2 || rail === 3 || rail === 4 || rail === 5) return true;
        break;
      case 2:
        if (rail === 1 || rail === 3 || rail === 5 || rail === 6) return true;
        break;
      case 3:
        if (rail === 2 || rail === 3 || rail === 6 || rail === 7) return true;
        break;
    }
    return false;
  };

  // 선로 만나서 바뀌는 방향
  const meetRail = (d, rail) => {
    if (rail === 1 || rail === 2 || rail === 3) return d;

    switch (rail) {
      case 4:
        if (d === 0) return 3;
        if (d === 1) return 2;
      case 5:
        if (d === 1) return 0;
        if (d === 2) return 3;
      case 6:
        if (d === 2) return 1;
        if (d === 3) return 0;
      case 7:
        if (d === 0) return 1;
        if (d === 3) return 2;
    }
    console.log("error");
  };

  // s로 들어와서 e방향으로 바뀌는 레일번호 반환
  const getRail = (s, e) => {
    switch (s) {
      case 0:
        switch (e) {
          case 1:
            return 7;
          case 3:
            return 4;
        }
      case 1:
        switch (e) {
          case 0:
            return 5;
          case 2:
            return 4;
        }
      case 2:
        switch (e) {
          case 1:
            return 6;
          case 3:
            return 5;
        }
      case 3:
        switch (e) {
          case 0:
            return 6;
          case 2:
            return 7;
        }
    }
    console.log("wrong d");
  };

  // d: 진입 방향
  const dfs = (x, y, d, cnt) => {
    let rail = grid[x][y];
    if (rail > 0) {
      cnt--;
      // 이미 선로가 있는경우: 원래 깔린 선로거나, 3번을 설치하고 처음 지나가거나
      if (canPass(d, rail)) {
        if (x === n - 1 && y === m - 1) {
          if (cnt === 0) answer++;
          return;
        }
        let nd = meetRail(d, rail);
        let [dx, dy] = delta[nd];
        let [nx, ny] = [x + dx, y + dy];
        if (nx < 0 || nx >= n || ny < 0 || ny >= m || grid[nx][ny] === -1)
          return; // 함수가 돌면서 3번을 벽에 붙여서 설치한 경우
        dfs(nx, ny, nd, cnt);
        return;
      }
      return;
    }

    // 현재 진입 방향에서 나올 수 있는 선로를 설치하기
    for (let cRail = 1; cRail < 8; cRail++) {
      if (!canPass(d, cRail)) continue;
      let nd = meetRail(d, cRail);
      let plus = 0;
      if (cRail === 3) plus++;

      if (Math.abs(d - nd) === 2) continue; // 반대로 못감
      let [dx, dy] = delta[nd];
      let [nx, ny] = [x + dx, y + dy];
      if (nx < 0 || nx >= n || ny < 0 || ny >= m || grid[nx][ny] === -1)
        continue;

      grid[x][y] = cRail;
      dfs(nx, ny, nd, cnt + plus);
      grid[x][y] = 0;
    }

    return;
  };

  dfs(0, 1, 0, cnt - 1);

  return answer;
}
