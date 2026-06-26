/**
방벙1.
현재 앱 큐에서 꺼내기.
현재 앱 이동. 해당 방향으로 한칸. 이동하면서 다른 인덱스 있으면 방향에 맞춰 가까운 순으로 큐에 저장
movedBoard 위에 현재 앱 위치(범위)에 앱이 있으면 해당 방향으로 이동. 넘어가는것도 포함.
최종 위치 찾아서 movedBoard에 그림.
-> 방향마다 돌려서 처리하기 -> 가로 세로 길이 항상 새로 구해야함

방법2.
현재 앱과 연결된 거 있는지 싹 다 좌표 저장
현재 앱 밀기 -> 좌표 싹다 임시 배열에 저장 -> 좌표 넘어가는 경우 일단 잘라서 저장 -> 잘라진 좌표에 해당하는 앱만 좌표 저장 -> 옮김 -> 잘라진 앱 저장 -> 옮김 반복
*/





function solution(board, commands) {
    const delta = [
    [0, 0],
    [0, 1],
    [1, 0],
    [0, -1],
    [-1, 0],
    ];
    const n = board.length;
    const m = board[0].length;
    const movedBoard = [...Array(n)].map(()=>Array(m).fill(0));

    // 해당 부분으로 이동
    
    const move = (number, direction, queue) => {};

    // 현재 좌표에 해당하는 앱의 고유번호, 크기, 왼쪽의 좌표 반환
    const getAppInfo = (x, y) => {
        if (board[x][y] === 0) return [null, null, null, null];
        let index = board[x][y];
        let queue = [[x, y]];
        let leftUpX = x;
        let leftUpY = y;
        let maxY = y;

        let visited = [...Array(n)].map(() => Array(m).fill(false));
        visited[x][y] = true;
        while (queue.length > 0) {
          let [x, y] = queue.shift();

          for (let d = 1; d < 5; d++) {
            let [dx, dy] = delta[d];
            let [nx, ny] = [x + dx, y + dy];
            if (nx < 0 || nx >= n || ny < 0 || ny >= m || visited[nx][ny]) continue;
            if (board[nx][ny] !== index) continue;

            leftUpX = Math.min(leftUpX, nx);
              leftUpY = Math.min(leftUpY, ny);
              maxY = Math.max(maxY, ny);
            visited[nx][ny] = true;
            queue.push([nx, ny]);
          }
        }

        let width = maxY - leftUpY + 1;
        return [index, width, leftUpX, leftUpY];
    };

    // 커맨드에 따라서 로직 진행
    
    // 커맨드에 따라 이동할 앱 탐색
    
    // 해당 앱이 이동하는 자리(앱이 넘어가는 경우 포함)에 존재하는 모든 앱 인덱스 찾아서 정보(getAppInfo) 저장해놓기 + 얼마나 이동해야하는지 체크(1 or 앱이 넘어가면 앱 크기만큼)
    // 큐로 진행하기 특정 "인덱스"의 앱을 어떤 "방향"으로 "얼마나" 이동할지 저장. 이때, 해당 앱의 정보도 같이 저장 ex) 앱 인덱스, 왼쪽 위 위치, 방향, 이동거리
    // -> 이동거리 빼야함 왜냐면 정확한 이동거리가 산정이 안됨. 너무 경우의 수가 많아서 그냥 밀어낸다고 치고, 밀릴 때 순서대로 배치하고 해당 자리가 차 있으면 해당 방향에 차곡차곡 쌓기
    // 앱이 이동한다면 앱이 이동하는 범위에 존재하는 모든 앱의 정보를 가지고 있어야함.
    for (let [index, direction] of commands) {
        // 현재 앱 위치 파악
        let width, leftUpX, leftUpY;
        loop1: for (let i = 0; i < n; i++) {
            for (let j = 0; j < m; j++) {
                if (board[i][j] === index) {
                    [_, width, leftUpX, leftUpY] = getAppInfo(i, j);
                    break loop1;
                }
            }
        }
        
        // 현재 앱 포함 이동시키면서 앱 배치
        let queue = [];
        let visited = new Set([0, index]);

        // 넘어가는지 체크. 방향에 따라 넘어가는지 여부가 다름. 그 범위에 있는 앱들 가까운 순서대로 저장하고 쌓아가기
        let distance = 1;
        if (direction === 1) {
            // 오른쪽
            // 오른쪽에 붙어 있어서 넘어가는지 체크
            if (leftUpY + width - 1 === m - 1) distance = width;
            for (let j = leftUpY + width; j < leftUpY + width + distance; j++) {
                let nj = j % m;
                for (let i = leftUpX; i < leftUpX + width; i++) {
                    let nid = board[i][nj];
                    if (visited.has(nid)) continue;
                    visited.add(nid);
                    queue.push(nid);
                }
            }
        } else if (direction === 2) {
            // 아래
            // 아래에 붙어 있어서 넘어가는지 체크
            if (leftUpX + width - 1 === n - 1) distance = width;
            for (let i = leftUpX + width; i < leftUpX + width + distance; i++) {
                let ni = i % n;
                for (let j = leftUpY; j < leftUpX + width; j++) {
                    let nid = board[ni][j];
                    if (visited.has(nid)) continue;
                    visited.add(nid);
                    queue.push(nid);
                }
            }
        } else if (direction === 3) {
            // 왼쪽
            // 왼쪽에 붙어 있어서 넘어가는지 체크
            if (leftUpY === 0) distance = width;
            for (let j = leftUpY-1; j > leftUpY - distance; j--) {
                let nj = (j+m) % m;
                for (let i = leftUpX; i < leftUpX + width; i++) {
                    let nid = board[i][nj];
                    if (visited.has(nid)) continue;
                    visited.add(nid);
                    queue.push(nid);
                }
            }

        } else if (direction === 4) {
            // 위
            // 위에 붙어 있어서 넘어가는지 체크
            if (leftUpX === 0) distance = width;
            for (let i = leftUpX - 1; i > leftUpX - distance; i--) {
                let ni = (i+n) % n;
                for (let j = leftUpY; j < leftUpX + width; j++) {
                    let nid = board[ni][j];
                    if (visited.has(nid)) continue;
                    visited.add(nid);
                    queue.push(nid);
                }
            }
        }
        console.log(queue);
    }
    
  return board;
}
