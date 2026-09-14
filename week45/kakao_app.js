/** 
board에서 하나씩 옮김
앱을 옮길 때는 시작하는 앱을 구하고 해당 앱이 미는 앱들을 구함 -- 1 makeSet, 좌표를 기준으로 bfs를 돌리면 겁나 헷갈림. 그래서 앱 id로 bfs를 돌리고 해당 앱이 있는 구역을 또 찾아서 이동방향에 있는 앱 찾기
해당 그룹 한칸 밀기 -- 2 moveSet
한칸 밀고 격자판에 의해 잘린 앱들 구함 -- 3
잘린 앱들을 시작으로 미는 앱들을 구함 -- 123 반복
*/

function solution(board, commands) {
  const n = board.length;
  const m = board[0].length;

  const delta = [
    [0, 1],
    [1, 0],
    [0, -1],
    [-1, 0],
  ];

  const makeSet = (id, direction) => {
    // 해당 앱과 direction 방향으로 붙어있는 앱들 다 같은 set으로 묶어서 반환 (n^2)
    let queue = [id];
    let ret = new Set([id]);

    // bfs돌림, 같이 묶이는 앱을 주어진 방향으로 이동해서 다른 앱이 있는 경우 큐에 넣어서 탐색
    while (queue.length > 0) {
      let cid = queue.shift();
      board.forEach((line, i) => {
        line.forEach((id, j) => {
          if (cid !== id) return;
          let [dx, dy] = delta[direction];
          let [nx, ny] = [(i + dx + n) % n, (j + dy + m) % m];
          let nid = board[nx][ny];
          if (ret.has(nid) || nid === 0) return;
          ret.add(nid);
          queue.push(nid);
        });
      });
    }
    return ret;
  };

  const moveSet = (apps, direction) => {
    // app들 한칸 이동
    // 현재 위치들 저장 => app id 도 저장
    let points = [];
    board.forEach((line, i) => {
      line.forEach((id, j) => {
        if (apps.has(id)) points.push([i, j, id]);
      });
    });
    // 지우고
    points.forEach(([x, y, _], i) => {
      board[x][y] = 0;
    });
    // 옮긴위치에 다시 표시
    let [dx, dy] = delta[direction];
    points.forEach(([x, y, id], i) => {
      let [nx, ny] = [(x + dx + n) % n, (y + dy + m) % m];
      board[nx][ny] = id;
    });
  };

  const getBrokenApps = (direction) => {
    let ret = new Set();

    // 좌우랑 상하 나눠서 판단, 처음과 끝에 있는데 중간이 비어있으면 잘린 앱
    // 좌우
    if (direction === 0 || direction === 2) {
      let [sj, ej] = [0, m - 1];
      for (let i = 0; i < n; i++) {
        if (board[i][sj] === 0 || board[i][sj] !== board[i][ej]) continue;
        let id = board[i][sj];
        if (ret.has(id)) continue;
        for (let j = 1; j < m - 1; j++) {
          // 중간이 비면 추가
          if (board[i][j] !== id) {
            ret.add(id);
            break;
          }
        }
      }
    }
    if (direction === 1 || direction === 3) {
      let [si, ei] = [0, n - 1];
      for (let j = 0; j < m; j++) {
        if (board[si][j] === 0 || board[si][j] !== board[ei][j]) continue;
        let id = board[si][j];
        if (ret.has(id)) continue;
        for (let i = 1; i < n - 1; i++) {
          // 중간이 비면 추가
          if (board[i][j] !== id) {
            ret.add(id);
            break;
          }
        }
      }
    }
    return ret;
  };

  commands.forEach(([id, direction]) => {
    direction--;

    // 현재 움직이는 앱 방향으로 같이 움직일 앱들 묶기
    let apps = makeSet(id, direction);
    // 한칸 움직이기
    moveSet(apps, direction);

    // 잘려서 추가로 움직이는 앱들 처리
    while (true) {
      // 잘린 앱들 구하기
      let brokenApps = getBrokenApps(direction);
      // 잘립 앱이 없으면 그만
      if (brokenApps.size === 0) break;

      let id = [...brokenApps][0];
      let apps = makeSet(id, direction);
      moveSet(apps, direction);

      // 잘린 앱과 같이 이동하는 앱들 묶어서 같이 이동시키기 (오답: 시간초과, 이유: 두 앱이 잘려있고 둘 중에 하나의 앱이 밀어내는 중간의 앱이 다른 앱도 밀어버리는 경우에 무한루프 발생)
      // brokenApps.forEach((id, i)=>{
      //     let apps = makeSet(id, direction);
      //     moveSet(apps, direction);
      // })
    }
  });

  return board;
}
