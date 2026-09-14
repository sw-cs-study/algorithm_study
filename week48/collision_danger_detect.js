// O(N^3)로도 가능?? 좌표평면이 100*100이라 최대 이동 길이가 200이고, 로봇의 수는 100개. 로봇의 route 길이도 100개이므로.

function solution(points, routes) {
  var answer = 0;
  let state = []; // [현재좌표, 로봇idx, 목표 지점idx]
  for (let i = 0; i < routes.length; i++) {
    state.push([
      points[routes[i][0] - 1][0] - 1,
      points[routes[i][0] - 1][1] - 1,
      i,
      1,
    ]);
  }
  answer += check(state);

  while (state.length) {
    let nextState = [];
    // 현재 이동 중인 로봇들을 이동 시킴
    for (let [x, y, rIdx, tIdx] of state) {
      let [tx, ty] = [
        points[routes[rIdx][tIdx] - 1][0] - 1,
        points[routes[rIdx][tIdx] - 1][1] - 1,
      ];

      // 목표 지점 도착
      if (x === tx && y === ty) {
        if (tIdx + 1 < routes[rIdx].length) {
          [tx, ty] = [
            points[routes[rIdx][tIdx + 1] - 1][0] - 1,
            points[routes[rIdx][tIdx + 1] - 1][1] - 1,
          ];
          tIdx += 1;
        } else {
          continue;
        }
      }
      let np = move(x, y, tx, ty);
      nextState.push([np[0], np[1], rIdx, tIdx]);
    }
    // 로봇들이 겹쳐있는지 확인
    answer += check(nextState);
    state = nextState;
  }

  return answer;
}

function check(arr) {
  // 위험한 상황 갯수 반환
  const board = [...Array(101)].map(() => [...Array(101)].map(() => 0));
  let ret = 0;
  for (let [x, y, a, b] of arr) {
    if (board[x][y] === 1) {
      ret += 1;
      board[x][y] = 2;
    } else if (board[x][y] === 0) {
      board[x][y] = 1;
    }
  }
  return ret;
}

// 현재 지점과 목표지점까지 최단 경로로 r좌표 이동을 우선할 때, 다음 좌표
function move(x, y, nx, ny) {
  if (x < nx) {
    return [x + 1, y];
  } else if (x > nx) {
    return [x - 1, y];
  } else {
    if (y < ny) {
      return [x, y + 1];
    } else {
      return [x, y - 1];
    }
  }
}
