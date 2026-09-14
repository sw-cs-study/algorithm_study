// 완탐
// 매번 지도를 지날때마다 선인장 구역 탐색 : 250,000^2 으로 터짐

//

function solution(m, n, h, w, drops) {
  let board = [...Array(m)].map(() => Array(n).fill(Infinity));

  drops.forEach(([x, y], i) => {
    board[x][y] = i;
  });

  let row_min = []; // 행마다 w길이씩 잘라서 볼때 최솟값들 저장
  board.forEach((line, i) => {
    let row = [];
    let deque = []; // [v, j];
    let head = 0;

    line.forEach((v, j) => {
      if (deque.length === 0) {
        deque.push([v, j]);
      } else {
        // 현재 위치를 끝으로 잡을 때 앞에 범위가 벗어나는 값들은 제거
        while (head < deque.length) {
          let first = deque[head];
          if (first[1] > j - w) break;
          head++;
        }

        // 들어오는 값이 덱 마지막 값보다 작으면 작은 값들은 다 제거함.
        while (deque.length > head) {
          let last = deque.pop();
          // v 값이 더 클 때까지 진행
          if (v > last[0]) {
            deque.push(last);
            break;
          }
        }
        deque.push([v, j]);
      }

      // 현재 값까지 진행하고 최솟값 저장
      if (j >= w - 1) row.push(deque[head][0]);
    });

    row_min.push(row);
  });

  // row_min을 토대로 세로로 다시 실행하면 h*m 범위에서 최솟값들을 구할 수 있음. 해당 값들중에 가장 큰 값을 고르거나 같다면 왼쪽 위를 우선으로 골라서 범위 시작 지점을 계산하면 정답
  let grid_min = [];
  for (let j = 0; j < row_min[0].length; j++) {
    let column = [];
    let deque = [];
    let head = 0;

    for (let i = 0; i < row_min.length; i++) {
      let v = row_min[i][j];
      if (deque.length === 0) {
        deque.push([v, i]);
      } else {
        // 현재 위치를 끝으로 잡을 때 앞에 범위가 벗어나는 값들은 제거
        while (head < deque.length) {
          let first = deque[head];
          if (first[1] > i - h) break;
          head++;
        }

        // 들어오는 값이 덱 마지막 값보다 작으면 작은 값들은 다 제거함.
        while (deque.length > head) {
          let last = deque.pop();
          // v 값이 더 클 때까지 진행
          if (v > last[0]) {
            deque.push(last);
            break;
          }
        }
        deque.push([v, i]);
      }

      // 현재 값까지 진행하고 최솟값 저장
      if (i >= h - 1) column.push(deque[head][0]);
    }
    grid_min.push(column);
  }

  var answer = [-1, -1];
  let max_value = -1;
  for (let i = 0; i < grid_min[0].length; i++) {
    for (let j = 0; j < grid_min.length; j++) {
      let v = grid_min[j][i];
      if (v > max_value) {
        answer = [i, j];
        max_value = v;
      }
    }
  }

  return answer;
}
