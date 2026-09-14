// 완탐
// 패널을 순서대로 건드려야함. 패널을 순서대로 나열하고 dfs를 진행하며 다음 패널로의 이동은 bfs를 이용.
// 패널 순서대로 나열하는 복잡도 : O(n!) => 15!
// 이동할 때 bfs 복잡도 : O(n) 16000

// 줄이기
// 패널 순서대로 나열하는 건 어케 줄이지 최소 시간이라 다익스트라 깔인데.... 근데 순서는 또 위상 정렬 깔이고...
// 패널 순서가 하나만 있다면 모든 패널로 순열 조져야함... 이때는 그냥 다익스트라 쓰면 O(V^2) = 15^2
// 같은 위상인 애들만 다익스트라?? => ㄴㄴㄴ

// dp?? 탑다운으로 진행, dfs + 메모이제이션
// 위상정렬로 0부터 방문지점의 위상을 부여하고... 순서가 상관없는 방문지점끼리도 간접적으로 위상이 나눠져서 최적해가 안나올수 있음.
// 방문한 panel을 저장(비트마스킹)해서 현재 방문할 panel을 활성화하기 위한 준비가 되었는지 판단?? -> 각 패널마다 필요한 패널 따로 저장해놔야함.
// 방문처리 : 활성화한 패널, 직전에 방문한 패널 => 2^15, 15 => 가능
// 이동할 때 bfs 는 15개의 패널에 대해 미리 구해놓기 15C2 * 16000 = 105 * 16000

function solution(h, grid, panels, seqs) {
  const k = panels.length; // 0-base
  const [n, m] = [grid.length, grid[0].length];

  // bfs 용
  const delta = [
    [-1, 0],
    [0, 1],
    [1, 0],
    [0, -1],
  ];
  const check = (x, y) => {
    if (x < 0 || x >= n) return false;
    if (y < 0 || y >= m) return false;
    return true;
  };

  // 활성화에 필요한 패널들 저장, 1-base
  const formerPanel = [...Array(k + 1)].map(() => []);
  seqs.forEach((line, i) => {
    let [a, b] = line;
    formerPanel[b].push(a);
  });

  // 각 패널 거리 미리 구하기, 1-base
  let dist = [...Array(k + 1)].map(() => Array(k + 1).fill(Infinity));
  for (let i = 0; i < k + 1; i++) {
    dist[i][i] = 0;
  }
  let elevator = [-1, -1];
  grid.forEach((line, i) => {
    for (let j = 0; j < m; j++) {
      let v = line[j];
      if (v === "@") elevator = [i, j];
    }
  });
  panels.forEach((line, i) => {
    let [floor, r, c] = line.map((v) => v - 1);

    // bfs
    let queue = [[...elevator, 0]];
    let visited = [...Array(n)].map(() => Array(m).fill(false));
    visited[elevator[0]][elevator[1]] = true;
    while (queue.length > 0) {
      let [x, y, cnt] = queue.shift();

      for (let d = 0; d < 4; d++) {
        let [dx, dy] = delta[d];
        let [nx, ny] = [x + dx, y + dy];
        if (!check(nx, ny) || visited[nx][ny] || grid[nx][ny] === "#") continue;
        let nCnt = cnt + 1;
        if (nx === r && ny === c) {
          dist[0][i + 1] = nCnt;
          dist[i + 1][0] = nCnt;
          return;
        }
        queue.push([nx, ny, nCnt]);
        visited[nx][ny] = true;
      }
    }
  });
  for (let i = 1; i < k + 1; i++) {
    for (let j = i + 1; j < k + 1; j++) {
      let elveTime = Math.abs(panels[i - 1][0] - panels[j - 1][0]);
      let dis = dist[0][i] + elveTime + dist[0][j];
      // 같은 층이면 거리 다시 구하기
      if (elveTime === 0) {
        let [x, y] = [panels[i - 1][1] - 1, panels[i - 1][2] - 1];
        let [tx, ty] = [panels[j - 1][1] - 1, panels[j - 1][2] - 1];
        let queue = [[x, y, 0]];
        let visited = [...Array(n)].map(() => Array(m).fill(false));
        visited[x][y] = true;
        loop1: while (queue.length > 0) {
          let [x, y, cnt] = queue.shift();

          for (let d = 0; d < 4; d++) {
            let [dx, dy] = delta[d];
            let [nx, ny] = [x + dx, y + dy];
            if (!check(nx, ny) || visited[nx][ny] || grid[nx][ny] === "#")
              continue;
            let nCnt = cnt + 1;
            if (nx === tx && ny === ty) {
              dis = cnt + 1;
              break loop1;
            }
            queue.push([nx, ny, nCnt]);
            visited[nx][ny] = true;
          }
        }
      }
      dist[i][j] = dis;
      dist[j][i] = dis;
    }
  }

  // dfs
  const visited = [...Array(1 << k)].map(() => Array(k + 1).fill(Infinity)); // 1-base

  const dfs = (state = 0, before = 1, time = 0) => {
    // 모든 패널 활성화 완료
    if (state === (1 << k) - 1) {
      if (time < answer) answer = time;
      return;
    }

    if (visited[state][before] < time) return;
    visited[state][before] = time;

    // 다음에 갈 수 있는 패널 탐색
    for (let np = 1; np < k + 1; np++) {
      if (((1 << (np - 1)) & state) > 0) continue; // 이전에 활성화한 패널이면 패쓰
      // 먼저 활성화해야하는 패널들 체크 // 비트마스킹 제대로 바꾸기
      let activeFormerPanel = true;
      for (let v of formerPanel[np]) {
        if (((1 << (v - 1)) & state) === 0) {
          activeFormerPanel = false;
          break;
        }
      }
      if (activeFormerPanel) {
        let nTime = time + dist[before][np];
        dfs(state | (1 << (np - 1)), np, nTime);
      }
    }
    return;
  };

  let answer = Infinity;
  dfs();

  return answer;
}
